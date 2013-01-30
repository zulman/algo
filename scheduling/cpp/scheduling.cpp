/* 
 * File:   main.cpp
 * Author: oleg.chumakov
 *
 * Created on January 24, 2013, 10:11 AM
 */
#include <string>
#include <vector>
#include <map>
#include <set>
#include <queue>
#include <stack>
#include <cstdlib>
#include <cstring>
#include <cassert>
#include <iostream>
#include <sstream>
#include <cstddef>
#include <algorithm>
#include <utility>
#include <iterator>
#include <numeric>
#include <list>
#include <complex>
#include <cstdio>
#include <climits>
#include <fcntl.h>
#include <unistd.h>

using namespace std;

static void redirect(int argc, const char **argv) {
  if (argc > 1) {
    int fd = open(argv[1], O_RDONLY);
    if (fd == -1) {
      perror(argv[1]);
      exit(1);
    }
    if (-1 == dup2(fd, 0)) {
      perror(argv[1]);
      exit(1);
    }
    if (-1 == close(fd)) {
      perror(argv[1]);
      exit(1);
    }
  }

  if (argc > 2) {
    int fd = open(argv[2], O_WRONLY | O_CREAT, 0666);
    if (fd == -1) {
      perror(argv[2]);
      exit(1);
    }
    if (-1 == dup2(fd, 1)) {
      perror(argv[2]);
      exit(1);
    }
    if (-1 == close(fd)) {
      perror(argv[2]);
      exit(1);
    }
  }
}

struct jobsdiffcomp {

  bool operator() (const pair<int, int>& lhs, const pair<int, int>& rhs) const {
    //first weight
    //second length
    int l = lhs.first - lhs.second;
    int r = rhs.first - rhs.second;
    if (l == r)
      return lhs.first > rhs.first;
    return l>r;
  }
};

struct jobsratiocomp {

  bool operator() (const pair<int, int>& lhs, const pair<int, int>& rhs) const {
    //first weight
    //second length
    double l = (double)lhs.first / (double)lhs.second;
    double r = (double)rhs.first / (double)rhs.second;
    if (l == r)
      return lhs.first > rhs.first;
    return l>r;
  }
};

int main(int argc, const char **argv) {
  redirect(argc, argv);

  int cases;
  cin >> cases;
  set <pair<int, int>, jobsratiocomp > jobs;
  set <pair<int, int>, jobsratiocomp >::iterator iter;
  for (int cas = 0; cas < cases; cas++) {
    int W, P;
    cin >> W;
    cin >> P;
    pair <int, int> job(W, P);
    jobs.insert(job);
  }
  int c = 0;
  unsigned long long result = 0;
  for (iter = jobs.begin(); iter != jobs.end(); iter++) {
    c += (*iter).second;
    result += c * (*iter).first;
    printf("W: %d P: %d [%llu]\n", (*iter).first, (*iter).second, result);
  }
  return 0;
}
