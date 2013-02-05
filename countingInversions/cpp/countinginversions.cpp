/* 
 * File:   countinginversions.cpp
 * Author: oleg.chumakov
 *
 * Created on February 4, 2013, 10:09 AM
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

int merge(int a[], int left, int mid, int right) {
  int invCount = 0;

  int i = 1;
  int j = 1;
  int n = right - left + 1;
  int hsize = mid - left;
  int d[n];

  for (int k = 1; k < n; k++) {
    int b = a[left + i];
    int c = a[right + j];
    if (b <= c) {
      d[k] = b;
      i++;
    } else {
      d[k] = c;
      invCount += hsize - i;
      j++;
    }
  }

  for (int i = 0; i < n; i++) {
    a[left + i] = d[i];
  }

  return invCount;
}

int mergesort(int a[], int left, int right) {
  int result = 0;
  if (left < right) {
    int mid = (left + right) / 2;
    result += mergesort(a, left, mid);
    result += mergesort(a, mid + 1, right);
    result += merge(a, left, mid, right);
  }
  return result;
}

int inversions(int a[], int len) {
  if (len = 1)
    return 0;
  return mergesort(a, 0, len - 1);
}

int main(int argc, const char **argv) {
  redirect(argc, argv);
  vector<long> vec;
  int i = 0;
  cin.ignore(1024, '\n');
  while (!cin.eof()) {
    cin >> i;
    vec.push_back(i);
  }

  int a [vec.size()];
  //  long temp [vec.size()];
  copy(vec.begin(), vec.end(), a);
  //  copy(vec.begin(), vec.end(), temp);
  int r = inversions(a, vec.size());
  printf("inversions: %d", r);
  return 0;
}


