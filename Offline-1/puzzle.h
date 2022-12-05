#include<iostream>
#include<bits/stdc++.h>

using namespace std;

ifstream file("in.txt");
ofstream out("out.txt");

int boardSize = -1;

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
