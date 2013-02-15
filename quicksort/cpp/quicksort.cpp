/* 
 * File:   quicksort.cpp
 * Author: oleg.chumakov
 *
 * Created on February 13, 2013, 3:00 PM
 */

#include <vector>
#include <iostream>
#include <fcntl.h>

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

enum pivotmode {
  FIRST, LAST, MEDIAN
};

template<class T>
void qsortmodes(T* a, long N, long &count, pivotmode mode) {
  if (N < 1)
    return;
  count += N;
  T temp, p;
  int pidx = 0;
  if (mode == FIRST)
    //First
    pidx = 0;

  else if (mode == LAST)
    // Last
    pidx = N;
  else if (mode == MEDIAN) {
    //Median
    T s = a[0];
    T e = a[N];
    T m;
    int midx = 0;
    if ((N + 1) % 2 == 0)
      midx = (int) ((N + 1) / 2) - 1;
    else
      midx = (int) (1 + (N + 1) / 2) - 1;
    m = a[midx];
    int sum = e + s + m;
    int mx = max(s, max(e, m));
    int mn = min(s, min(e, m));
    temp = sum - mx - mn;
    if (temp == s)
      pidx = 0;
    else if (temp == e)
      pidx = N;
    else pidx = midx;
  }
  p = a[pidx];

  temp = a[pidx];
  a[pidx] = a[0];
  a[0] = temp;

  int i = 1;
  for (int j = 1; j <= N; j++)
    if (a[j] < p) {
      temp = a[i];
      a[i] = a[j];
      a[j] = temp;
      i++;
    }

  temp = a[0];
  a[0] = a[i - 1];
  a[i - 1] = temp;

  qsortmodes(a, i - 2, count, mode);
  qsortmodes(a + i, N - i, count, mode);
}

//https://d19vezwu8eufl6.cloudfront.net/algo1/slides%2Falgo-qsort-partition-annotated.pdf
//https://class.coursera.org/algo-003/class
//https://gist.github.com/zulman/4958850

int main(int argc, const char **argv) {

  int tt = 1 + 5 / 2;

  redirect(argc, argv);
  vector<long> vec;
  int i = 0;
  while (!cin.eof()) {
    cin >> i;
    vec.push_back(i);
  }
  long count = 0;
  int a [vec.size()];
  copy(vec.begin(), vec.end(), a);
  printf("Number of comparisons for different pivot's:\n");
  qsortmodes(a, vec.size() - 1, count, FIRST);
  printf("First array element: %d\n", count);

  copy(vec.begin(), vec.end(), a);
  count = 0;
  qsortmodes(a, vec.size() - 1, count, LAST);
  printf("Last array element: %d\n", count);

  copy(vec.begin(), vec.end(), a);
  count = 0;
  qsortmodes(a, vec.size() - 1, count, MEDIAN);
  printf("Median array element: %d\n", count);

  return 0;
}



