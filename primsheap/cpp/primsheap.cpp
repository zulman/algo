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

struct edge {
  int n1;
  int n2;
  int cost;

  explicit edge(int n1, int n2, int cost) : n1(n1), n2(n2), cost(cost) {
  }
};

struct vert {
  int id;
  int cost;

  bool operator==(const vert & d) const {
    return (id == d.id);
  }

  bool operator==(const int & d) const {
    return (id == d);
  }

  explicit vert(int id, int cost) : id(id), cost(cost) {
  }
};

struct vert_greater_comparer {

  bool operator()(vert const& a, vert const& b) const {
    return a.cost > b.cost;
  }
};

void printgraph(vector<edge> graph) {
  vector<edge>::iterator iter;
  for (iter = graph.begin(); iter != graph.end(); iter++) {
    edge i = (*iter);
    printf("%d -> %d : %d\n", i.n1, i.n2, i.cost);
  }
}

int main(int argc, const char **argv) {
  redirect(argc, argv);

  int nodes, edges;
  cin >> nodes >> edges;
  vector<edge> graph;
  vector<vert> vx;
  vector<vert> x;
  vector<edge>::iterator eiter;
  vector<vert>::iterator viter;

  int vertcount = 0;
  for (int ed = 0; ed < edges; ed++) {
    int n1, n2, cost;
    cin >> n1 >> n2 >> cost;
    graph.push_back(edge(n1, n2, cost));
    vertcount = max(vertcount, max(n1, n2));
  }

  for (int i = 2; i <= vertcount; i++) {
    vx.push_back(vert(i, INT_MAX));
  }

  vert v = vert(1, 0);
  int result = 0;
  while (!vx.empty()) {
    x.push_back(v);
    if (v.cost == INT_MAX)
      result += v.cost;
    result += v.cost;
    for (eiter = graph.begin(); eiter != graph.end(); eiter++) {
      edge e = (*eiter);

      int id = -1;
      if (e.n1 == v.id)
        id = e.n2;
      if (e.n2 == v.id)
        id = e.n1;
      if (id != -1) {
        vert w = vert(id, e.cost);
        for (viter = vx.begin(); viter != vx.end(); viter++) {
          vert vxv = (*viter);
          if (vxv == w) {
            viter = std::find(vx.begin(), vx.end(), vxv);
            if (viter != vx.end())
              vx.erase(viter);
            vxv.cost = min(vxv.cost, w.cost);
            vx.push_back(vxv);
            std::push_heap(vx.begin(), vx.end(), vert_greater_comparer());
          }
        }
      }
    }
    v = vx.front();
    std::pop_heap(vx.begin(), vx.end(), vert_greater_comparer());
    vx.pop_back();
  }
  printf("MST cost: %d\n", result);
  return 0;
}
