#include<iostream>
#include<bits/stdc++.h>

using namespace std;

#define HAMMING 1
#define MANHATTAN 2

ifstream file("in.txt");
ofstream out("out.txt");

int boardSize = -1;

int dirX[4] = {0, 0, -1, 1}; // RIGHT-LEFT-UP-DOWN
int dirY[4] = {1, -1, 0, 0}; // RIGHT-LEFT-UP-DOWN

struct BoardNode{
private:
    vector<vector<int>> currentNode, parentNode;
    int mov;
public:
    int priority;
    BoardNode(){}
    BoardNode(vector<vector<int>> currentBoard, vector<vector<int>> parentBoard, int mov, int p){
        this->currentNode = currentBoard;
        this->parentNode = parentBoard;
        this->mov = mov;
        this->priority = p;
    }
    vector<vector<int>> getCurrentNode(){
        return this->currentNode;
    }
    vector<vector<int>> getParentNode(){
        return this->parentNode;
    }
    int getMove(){
        return this->mov;
    }
    int getPriority(){
        return this->priority;
    }
};

//Overload the < operator.
bool operator<(const BoardNode& node1, const BoardNode& node2) {
    return node1.priority < node2.priority;
}

//Overload the < operator.
bool operator>(const BoardNode& node1, const BoardNode& node2){
    return node1.priority > node2.priority;
}

bool isValidRowCol(int row,int col)
{
    if (row<0 || col<0 || row>=boardSize || col>=boardSize){
            return false;
    }
    return true;
}

bool isBoardsSame(vector<vector<int>> board1, vector<vector<int>> board2){
    for(int i=0;i<boardSize;i++){
        for(int j=0;j<boardSize;j++){
            if(board1[i][j]!=board2[i][j]){
                return false;
            }
        }
    }
    return true;
}

void printBoard(vector<vector<int>> board){
    for(int i=0;i<boardSize;i++){
        for(int j=0;j<boardSize;j++){
            if(board[i][j]){
                out << setw(3) << board[i][j] << " ";
            }
            else{
                out << setw(3) << " *" << " ";
            }
        }
        out << endl;
    }
    out << "---------------------------" << endl;
}

int getHammingDistance(vector<vector<int>> board, vector<vector<int>> target)
{
    int distance = 0;
    for(int i=0;i<boardSize;i++)
    {
        for(int j=0;j<boardSize;j++)
        {
            if(board[i][j] && (board[i][j] != target[i][j]))
                distance++;
        }
    }
    return distance;
}

int getManhattanDistance(vector<vector<int>> board)
{
    map<int, pair<int,int>> numPos, goalNumPos;
    int c=1;
    for(int i=0;i<boardSize;i++)
    {
        for(int j=0;j<boardSize;j++)
        {
            int x = board[i][j];
            numPos[x] = make_pair(i,j);
            goalNumPos[c++] = make_pair(i,j);
        }
    }
    int cnt = 0,l = boardSize*boardSize;
    for(int i=1;i<l;i++){
        cnt += abs(numPos[i].first-goalNumPos[i].first) + abs(numPos[i].second-goalNumPos[i].second);
    }
    return cnt;
}

int getInversion(vector<int>v){
    int inv_cnt=0, l=v.size();
    for(int i=0;i<l;i++){
        for(int j=i+1;j<l;j++){
            if(v[i] > v[j]){
                inv_cnt++;
            }
        }
    }
    return inv_cnt;
}

bool isPuzzleSolvable(vector<vector<int>>board){
    vector<int> v;
    int blank_row = -1;
    for(int i=0;i<boardSize;i++){
        for(int j=0;j<boardSize;j++){
            if(board[i][j]){
                v.push_back(board[i][j]);
            }
            else{
                blank_row = i;
            }
        }
    }
    int inv_cnt = getInversion(v);
    if((boardSize%2) && !(inv_cnt%2)){ // odd board & even inversions
        return true;
    }
    else if(!(boardSize%2) && ((blank_row+inv_cnt)%2)){ // even board & odd sum
        return true;
    }
    return false;
}

void printSolve(map<vector<vector<int>>, vector<vector<int>>> boardMap, vector<vector<int>> board){
    vector<vector<vector<int>>> solve;
    vector<vector<int>> bn;
    solve.push_back(board);
    bn = boardMap[board];
    while(1){
        if(bn[0][0]==-1){
            break;
        }
        else{
            solve.push_back(bn);
            bn = boardMap[bn];
        }
    }
    reverse(solve.begin(), solve.end());
    for(auto &i:solve){
        printBoard(i);
    }
}

void AStarSearch(vector<vector<int>> board, vector<vector<int>> targetBoard, int type){
    map<vector<vector<int>>, vector<vector<int>>> boardMap;
    int nExplored = 0, max_depth = 0, nExpended = 0;
    priority_queue<BoardNode, vector<BoardNode>, greater<BoardNode>> openList;
    vector<vector<int>>parent(boardSize,vector<int>(boardSize,-1));
    set<vector<vector<int>>>closedList;
    openList.push(BoardNode(board,parent,0,0));
    nExpended++;
    boardMap[board] = parent;

    while(!openList.empty()){
        BoardNode node = openList.top();
        openList.pop();
        nExplored++;
        vector<vector<int>> currentBoard = node.getCurrentNode();
        max_depth = max(max_depth, node.getMove());

        if(closedList.count(currentBoard)){
            continue;
        }
        else{
            closedList.insert(currentBoard);
        }

        if(isBoardsSame(currentBoard, targetBoard)){
            out << "No of Steps: " << node.getMove() << endl;
            printSolve(boardMap, currentBoard);
            break;
        }

        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                if(currentBoard[i][j]==0){
                    for(int x=0;x<4;x++){
                        int nextRow = i + dirX[x];
                        int nextCol = j + dirY[x];
                        if(isValidRowCol(nextRow, nextCol)){
                            vector<vector<int>> nextBoard = currentBoard;
                            swap(nextBoard[i][j], nextBoard[nextRow][nextCol]);
                            if(closedList.count(nextBoard)){
                                continue;
                            }
                            //printBoard(nextBoard);
                            int h;
                            if(type == HAMMING){
                                h = getHammingDistance(nextBoard, targetBoard);
                                //out << "Hamming dist: " << h << endl;
                            }
                            else{
                                h = getManhattanDistance(nextBoard);
                                //out << "Manhattan dist: " << h << endl;
                            }
                            nExpended++;
                            boardMap[nextBoard] = currentBoard;
                            openList.push(BoardNode(nextBoard, currentBoard, node.getMove()+1, node.getMove()+1+h));
                        }
                    }
                }
            }
        }
    }
    //int openedCount = closedList.size();
    out << "No of Nodes Explored: " << nExplored << endl;
	//out << "No of Nodes Opened: " << closedList.size() << endl;
	out << "No of Nodes Expended: " << nExpended << endl;
	//out << "Max Depth Reached: " << max_depth << endl;
	out << "****************END***************" << endl;
	out << endl;
	out << endl;
}
