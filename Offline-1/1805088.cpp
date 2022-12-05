#include "puzzle.h"

int str_to_num(string s)
{
    stringstream ss(s);
    int num;
    ss >> num;
    return num;
}

void printBoard(vector<vector<int>> board){
    for(int i=0;i<boardSize;i++){
        for(int j=0;j<boardSize;j++){
            if(board[i][j]){
                cout << setw(3) << board[i][j] << " ";
            }
            else{
                cout << setw(3) << " *" << " ";
            }
        }
        cout << endl;
    }
}

// Takes input of the initial board
vector<vector<int>> getInitialBoard(){
    vector<vector<int>> board(boardSize, vector<int>(boardSize,-1));
    vector<bool> isEntry(boardSize*boardSize, false); // Checks if same input is given more than one time. By default all are set to false.
    //string str;
    int x;
    for(int i=0;i<boardSize;i++){
        for (int j=0;j<boardSize;j++){
            /*cin >> str;
            if(str=="*"){
                board[i][j] = 0;
            }
            else{
                int x = str_to_num(str);
                if(x<=0 || (x>n*n-1) || (isEntry[x])){
                    cout << "Invalid input for initial board!!" << endl;
                    exit(1);
                }
                board[i][j] = x;
                isEntry[x] = true;
            }*/
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
            targetBoard[i][j] = i*boardSize + j;
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
    boardSize = m;
    vector<vector<int>> board = getInitialBoard();
    //printBoard(board,n);
    if(!isPuzzleSolvable(board)){
        cout << "Can't reach target board from the given initial board!!" << endl;
        return 1;
    }
    else{
        out << "    Using Hamming Distance Heuristics   " << endl;
        out << "----------------------------------------" << endl;
    }
    return 0;
}
