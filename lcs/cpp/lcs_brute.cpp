/* 
 * Created on May 30, 2013, 11:53:02 AM
 * File:   lcs_brute.cpp
 * Author: Oleg.Chumakov
 * Brute algorithm to cover set of 10 strings of 10 000 symbols
 * Usage example: %app% input.txt output.txt
 * Input format example: 
 * =======
 * 3
 * abacaba
 * mycabarchive
 * acabistrue
 * =======
 */

#include <iostream>
#include <string>
#include <stdio.h>
#include <vector>
#include "fcntl.h"

using namespace std;

// redirects input output to files passed from arguments
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
// calculates longest common substring via brute comparison
string getlcs(string a, string b) {
  const int a_size = a.size();
  const int b_size = b.size();

  const int result_size = b_size + 1;
  vector<int> x(result_size, 0), y(result_size);

  vector<int> * previous = &x;
  vector<int> * current = &y;
  int result_length = 0;
  int result_index = 0;
  int length = 0;
  
  for (int i = a_size - 1; i >= 0; i--) {
    for (int j = b_size - 1; j >= 0; j--) {
      int & current_match = (*current)[j];
      //match not found
      if (a[i] != b[j]) {
        current_match = 0;
      } else {
        //match found, updating result index if required
        length = 1 + (*previous)[j + 1];
        if (length > result_length) {
          result_length = length;
          result_index = i;
        }

        current_match = length;
      }
    }
    
    swap(previous, current);
  }

  return a.substr(result_index, result_length);
}

int main(int argc, const char **argv) {
  //redirecting cin/cout to files, passed via arguments
  redirect(argc, argv);
  int cases = 0;
  cin >> cases >> ws;

  std::vector<std::string> buf;
  std::string lcs, line;

  for (int cas = 0; cas < cases; cas++) {
    getline(cin, line);
    if (!line.size())
      continue;

    buf.push_back(line);
    if (buf.size() > 1) {
      lcs = getlcs(buf[0], buf[1]);
      buf.clear();
      buf.push_back(lcs);
    }
  }

  if (buf.size() && buf[0].size())
    std::cout << buf[0] << "\n";

  return 0;
}