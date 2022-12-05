#include "puzzle.h"

int str_to_num(string s)
{
    stringstream ss(s);
    int num;
    ss >> num;
    return num;
}

// Takes input of the initial board
vector<vector<int>> getInitialBoard(){
    vector<vector<int>> board(boardSize, vector<int>(boardSize,-1));
    vector<bool> isEntry(boardSize*boardSize, false); // Checks if same input is given more than one time. By default all are set to false.
    int x;
    for(int i=0;i<boardSize;i++){
        for (int j=0;j<boardSize;j++){
            file >> x;
            if(x<0 || (x>boardSize*boardSize-1) || (isEntry[x])){
                cout << "Invalid input for initial board!!" << endl;
                exit(1);
            }
            board[i][j] = x;
            isEntry[x] = true;
        }
    }
    return board;
}

// Creates default target board
vector<vector<int>> getTargetBoard(){
    vector<vector<int>> targetBoard(boardSize, vector<int>(boardSize,-1));
    for(int i=0;i<boardSize;i++){
        for (int j=0;j<boardSize;j++){
            targetBoard[i][j] = i*boardSize + j + 1;
        }
    }
    targetBoard[boardSize-1][boardSize-1] = 0;
    return targetBoard;
}

int main()
{
    if(!file.is_open()){
        cout << "File in.txt not found!!" << endl;
        return 1;
    }
    int n;
    file >> n;
    boardSize = n;
    vector<vector<int>> board = getInitialBoard();
    //printBoard(board);
    if(!isPuzzleSolvable(board)){
        cout << "Can't reach target board from the given initial board!!" << endl;
        return 1;
    }
    else{
        out << "Solvable" << endl;
        out << endl;
        vector<vector<int>> targetBoard = getTargetBoard();
        //printBoard(targetBoard);
        out << "    Using Hamming Distance Heuristics   " << endl;
        out << "----------------------------------------" << endl;
        AStarSearch(board, targetBoard, HAMMING);

        out << "    Using Manhattan Distance Heuristics   " << endl;
        out << "----------------------------------------" << endl;
        AStarSearch(board, targetBoard, MANHATTAN);
    }
    return 0;
}
