import itertools
import random


class Minesweeper():
    """
    Minesweeper game representation
    """

    def __init__(self, height=8, width=8, mines=8):

        # Set initial width, height, and number of mines
        self.height = height
        self.width = width
        self.mines = set()

        # Initialize an empty field with no mines
        self.board = []
        for i in range(self.height):
            row = []
            for j in range(self.width):
                row.append(False)
            self.board.append(row)

        # Add mines randomly
        while len(self.mines) != mines:
            i = random.randrange(height)
            j = random.randrange(width)
            if not self.board[i][j]:
                self.mines.add((i, j))
                self.board[i][j] = True

        # At first, player has found no mines
        self.mines_found = set()

    def print(self):
        """
        Prints a text-based representation
        of where mines are located.
        """
        for i in range(self.height):
            print("--" * self.width + "-")
            for j in range(self.width):
                if self.board[i][j]:
                    print("|X", end="")
                else:
                    print("| ", end="")
            print("|")
        print("--" * self.width + "-")

    def is_mine(self, cell):
        i, j = cell
        return self.board[i][j]

    def nearby_mines(self, cell):
        """
        Returns the number of mines that are
        within one row and column of a given cell,
        not including the cell itself.
        """

        # Keep count of nearby mines
        count = 0

        # Loop over all cells within one row and column
        """
        for i in range(cell[0] - 1, cell[0] + 2):
            for j in range(cell[1] - 1, cell[1] + 2):

                # Ignore the cell itself
                if (i, j) == cell:
                    continue

                # Update count if cell in bounds and is mine
                if 0 <= i < self.height and 0 <= j < self.width:
                    if self.board[i][j]:
                        count += 1
        """
                        
        for i in range(cell[0] - 1, cell[0] + 2):
            # Ignore the cell itself
            if (i, cell[1]) == cell:
                continue

            # Update count if cell in bounds and is mine
            if 0 <= i < self.height and self.board[i][cell[1]]:
                count += 1
        
        for j in range(cell[1] - 1, cell[1] + 2):
            # Ignore the cell itself
            if (cell[0], j) == cell:
                continue

            # Update count if cell in bounds and is mine
            if 0 <= j < self.width and self.board[cell[0]][j]:
                count += 1

        return count

    def won(self):
        """
        Checks if all mines have been flagged.
        """
        return self.mines_found == self.mines


class Sentence():
    """
    Logical statement about a Minesweeper game
    A sentence consists of a set of board cells,
    and a count of the number of those cells which are mines.
    """

    def __init__(self, cells, count):
        self.cells = set(cells)
        self.count = count

    def __eq__(self, other):
        return self.cells == other.cells and self.count == other.count

    def __str__(self):
        return f"{self.cells} = {self.count}"

    def known_mines(self):
        """
        Returns the set of all cells in self.cells known to be mines.
        """
        # If the count of mines is equal to the number of cells , then all cells are mines
        if len(self.cells) == self.count and self.count != 0:
            print('Mine Identified! - ', self.cells)
            return self.cells
        
        return set()

    def known_safes(self):
        """
        Returns the set of all cells in self.cells known to be safe.
        """
        # If the count of mines is 0, then all cells in the sentence are safe
        if self.count == 0:
            return self.cells
        
        return set()

    def mark_mine(self, cell):
        """
        Updates internal knowledge representation given the fact that
        a cell is known to be a mine.
        """
        # If the cell is in the sentence, then remove it and decrement count by one
        if cell in self.cells:
            self.cells.remove(cell)
            self.count -= 1

    def mark_safe(self, cell):
        """
        Updates internal knowledge representation given the fact that
        a cell is known to be safe.
        """
        # If the cell is in the sentence, then remove it, but don't decrement the count
        if cell in self.cells:
            self.cells.remove(cell)

class MinesweeperAI():
    """
    Minesweeper game player
    """

    def __init__(self, height=8, width=8):

        # Set initial height and width
        self.height = height
        self.width = width

        # Keep track of which cells have been clicked on
        self.moves_made = set()

        # Keep track of cells known to be safe or mines
        self.mines = set()
        self.safes = set()

        # List of sentences about the game known to be true
        self.knowledge = []

    def mark_mine(self, cell):
        """
        Marks a cell as a mine, and updates all knowledge
        to mark that cell as a mine as well.
        """
        self.mines.add(cell)
        for sentence in self.knowledge:
            sentence.mark_mine(cell)

    def mark_safe(self, cell):
        """
        Marks a cell as safe, and updates all knowledge
        to mark that cell as safe as well.
        """
        self.safes.add(cell)
        for sentence in self.knowledge:
            sentence.mark_safe(cell)

    def add_knowledge(self, cell, count):
        """
        Called when the Minesweeper board tells us, for a given
        safe cell, how many neighboring cells have mines in them.

        This function should:
            1) mark the cell as a move that has been made
            2) mark the cell as safe
            3) add a new sentence to the AI's knowledge base
               based on the value of `cell` and `count`
            4) mark any additional cells as safe or as mines
               if it can be concluded based on the AI's knowledge base
            5) add any new sentences to the AI's knowledge base
               if they can be inferred from existing knowledge
        """
        # 1. mark the cell as a move that has been made
        self.moves_made.add(cell)

        # 2. mark the cell as safe
        self.mark_safe(cell)
        
        # New sentence set to store undecided cells for KB:
        neighbors = set()
        i, j = cell
        neighbor_cells = [(i-1, j), (i+1, j), (i, j-1), (i, j+1)]
        
        #Loop over the neighbours of current cell
        for neighbor in neighbor_cells:
            x, y = neighbor
            #print(f'X is: {x} and Y is {y}')
            # Ignore the cell itself
            if (x, y) == cell:
                continue

            # If neighbor is already safe, then ignore it
            if (x, y) in self.safes:
                continue

            # If neighbor is known to be in mines, then reduce count by 1 and ignore it
            if (x, y) in self.mines:
                count = count - 1
                continue

            # Otherwise add it to sentence if it is in the game board
            if 0 <= x < self.height and 0 <= y < self.width:
                neighbors.add((x, y))
        
        # 3. Add the new sentence to the AI's Knowledge Base
        # based on the value of `cell` and `count`
        print(f'Move on cell: {cell} has added sentence to knowledge {neighbors} = {count}' )
        self.knowledge.append(Sentence(neighbors, count))
        
        # Iteratively mark guaranteed mines and safes and infer new knowledge
        knowledge_changed = True
        
        while knowledge_changed:
            knowledge_changed = False
            
            # 4. mark any additional cells as safe or as mines
            # if it can be concluded based on the AI's knowledge base
            knowledge_changed = self.mark_safes_and_mines()
            
            # Remove empty sentences from knowledge base
            empty = Sentence(set(), 0)
            self.knowledge[:] = [x for x in self.knowledge if x != empty]
            
            # 5. add any new sentences to the AI's knowledge base
            # if they can be inferred from existing knowledge
            updated = self.get_new_inference()
            # if new sentences was derived, it means
            # knowledge base was changed
            if updated:
                knowledge_changed = updated
        
        # Printing current AI knowledge
        print('Current AI KB length: ', len(self.knowledge))
        print('Known Mines: ', self.mines)
        print('Safe Moves Remaining: ', self.safes - self.moves_made)
        print('====================================================\n\n')    
            
        
    def mark_safes_and_mines(self):
        knowledge_changed = False
        safes = set()
        mines = set()
        
        # Getting the set of safe spaces and mines from KB
        for sentence in self.knowledge:
            safes = safes.union(sentence.known_safes())
            mines = mines.union(sentence.known_mines())

        # Mark any safe spaces or mines:
        if safes:
            knowledge_changed = True
            for safe in safes:
                self.mark_safe(safe)
        if mines:
            knowledge_changed = True
            for mine in mines:
                self.mark_mine(mine)
                
        return knowledge_changed
    
    def get_new_inference(self):
        changed = False
        
        # Try to infer new sentences from the current ones:
        for sentence_1 in self.knowledge:
            for sentence_2 in self.knowledge:
                # Ignore when sentences are identical
                if sentence_1.cells == sentence_2.cells:
                    continue

                if sentence_1.cells == set() and sentence_1.count > 0:
                    print('Error - sentence with no cells and count created')
                    raise ValueError

                # Create a new sentence if sentence_1 is a subset of sentence_2, and not in KB:
                if sentence_1.cells.issubset(sentence_2.cells):
                    new_sentence_cells = sentence_2.cells - sentence_1.cells
                    new_sentence_count = sentence_2.count - sentence_1.count

                    new_sentence = Sentence(new_sentence_cells, new_sentence_count)

                    # Add to knowledge if not already in KB:
                    if new_sentence not in self.knowledge:
                        changed = True
                        print('New Inferred Knowledge: ', new_sentence, 'from', sentence_1, ' and ', sentence_2)
                        self.knowledge.append(new_sentence)
        return changed
        

    def make_safe_move(self):
        """
        Returns a safe cell to choose on the Minesweeper board.
        The move must be known to be safe, and not already a move
        that has been made.

        This function may use the knowledge in self.mines, self.safes
        and self.moves_made, but should not modify any of those values.
        """
        # Getting set of safe cells and not a moves already made in that
        safe_moves = self.safes - self.moves_made
        if safe_moves:
            print('Making a Safe Move! Safe moves available: ', len(safe_moves))
            return random.choice(list(safe_moves))

        # Otherwise no guaranteed safe moves can be made
        return None

    def make_random_move(self):
        """
        Returns a move to make on the Minesweeper board.
        Should choose randomly among cells that:
            1) have not already been chosen, and
            2) are not known to be mines
        """
        # If no move can be made
        if len(self.mines) + len(self.moves_made) == self.height * self.width:
            return None

        # Loop until an appropriate move is found
        """
        while True:
            i = random.randrange(self.height)
            j = random.randrange(self.width)
            if (i, j) not in self.moves_made and (i, j) not in self.mines:
                return (i, j)
        """
        all_moves = set()
        for i in range(self.height):
            for j in range(self.width):
                if (i,j) not in self.mines and (i,j) not in self.moves_made:
                    all_moves.add((i,j))
        # No moves left
        if len(all_moves) == 0:
            return None
        # Return available
        move = random.choice(list(all_moves))
        return move