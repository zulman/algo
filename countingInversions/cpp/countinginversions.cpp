/* 
 * File:   countinginversions.cpp
 * Author: oleg.chumakov
 *
 * Created on February 4, 2013, 10:09 AM
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

unsigned long merge(int a[], int d[], int low, int mid, int high) {
  int i = low;
  int j = mid + 1;
  int k = low;
  unsigned long inversions = 0;
  while ((i <= mid) && (j <= high)) {
    int b = a[i];
    int c = a[j];
    //If using b<=c equal elements will be not readed as invertion
    //For homework at stanford course this condition is required
    if (b < c) {
      d[k] = b;
      i++;
    } else {
      d[k] = c;
      inversions += (mid - i + 1);
      j++;
    }
    k++;
  }

  if (i > mid) {
    for (i = j; i <= high; i++) {
      d[k] = a[i];
      k++;
    }
  } else {
    for (i; i <= mid; i++) {
      d[k] = a[i];
      k++;
    }
  }

  for (i = low; i <= high; i++) a[i] = d[i];
  return inversions;
}

unsigned long mergesort(int a[], int temp[], int left, int right) {
  unsigned long result = 0;
  if (left < right) {
    int mid = (left + right) / 2;
    result += mergesort(a, temp, left, mid);
    result += mergesort(a, temp, mid + 1, right);
    result += merge(a, temp, left, mid, right);
  }
  return result;
}

unsigned long inversions(int a[], int len) {
  if (len == 1)
    return 0;
  int temp[len];
  memcpy(temp, a, sizeof (a));
  return mergesort(a, temp, 0, len - 1);
}

//https://d19vezwu8eufl6.cloudfront.net/algo1/slides%2Falgo-inversions2.pdf
//https://gist.github.com/zulman/4720979

int main(int argc, const char **argv) {
  redirect(argc, argv);
  vector<long> vec;
  int i = 0;
  while (!cin.eof()) {
    cin >> i;
    vec.push_back(i);
  }

  int a [vec.size()];
  copy(vec.begin(), vec.end(), a);
  //  O(n^2)
  //  unsigned long r = 0;
  //  int n = vec.size();
  //  for (int i = 0; i < n - 1; i++) {
  //    for (int j = i + 1; j < n; j++) {
  //      if (a[i] > a[j]) {
  //        r++;
  //      }
  //    }
  //  }
  //  O(n*log(n))
  unsigned long r = inversions(a, vec.size());
  printf("items: %d, inversions: %lu", vec.size(), r);
  return 0;
}


