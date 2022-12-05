#include<iostream>
#include<bits/stdc++.h>

using namespace std;

ifstream file("in.txt");

int str_to_num(string s)
{
    stringstream ss(s);
    int num;
    ss >> num;
    return num;
}

void printBoard(vector<vector<int>> board, int n){
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
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
vector<vector<int>> getInitialBoard(int n){
    vector<vector<int>> board(n, vector<int>(n,-1));
    vector<bool> isEntry(n*n, false); // Checks if same input is given more than one time. By default all are set to false.
    //string str;
    int x;
    for(int i=0;i<n;i++){
        for (int j=0;j<n;j++){
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
            if(x<0 || (x>n*n-1) || (isEntry[x])){
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
vector<vector<int>> getTargetBoard(int n){
    vector<vector<int>> targetBoard(n, vector<int>(n,-1));
    for(int i=0;i<n;i++){
        for (int j=0;j<n;j++){
            targetBoard[i][j] = i*n + j;
        }
    }
    targetBoard[n-1][n-1] = 0;
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
    vector<vector<int>> board = getInitialBoard(n);
    printBoard(board,n);
    return 0;
}
