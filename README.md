# temp

## using Data1 or Data2
it works correctly because list is synchronized and atomics objects are used

```txt
Job#1680075645 fetching data.
Job#254077554 fetching data.
254077554-- get val: 9, cnt: 8
Job#1168666347 fetching data.
Job#1336998605 fetching data.
Job#469227557 fetching data.
1336998605-- get val: 7, cnt: 6
Job#240767393 fetching data.
1168666347-- get val: 8, cnt: 7
Job#222746741 fetching data.
Job#724053041 fetching data.
1680075645-- get val: 10, cnt: 9
724053041-- get val: 3, cnt: 2
222746741-- get val: 4, cnt: 3
240767393-- get val: 5, cnt: 4
469227557-- get val: 6, cnt: 5
254077554-- get val: 2, cnt: 1
1336998605-- get val: 1, cnt: 0
Job#1168666347 finished.
Job#1680075645 finished.
Job#724053041 finished.
Job#222746741 finished.
Job#240767393 finished.
Job#469227557 finished.
Job#254077554 finished.
Job#1336998605 finished.
Sum: 55
```

## using CrapData obj
it doesn't work good, anyways, it works as we can expect using this class :)

```txt
Job#1470333138 fetching data.
Job#1956394918 fetching data.
1470333138-- get val: 10, cnt: 9
Job#1336998605 fetching data.
Job#760554585 fetching data.
Job#186896924 fetching data.
Job#498974849 fetching data.
186896924-- get val: 9, cnt: 8
760554585-- get val: 9, cnt: 8
1336998605-- get val: 9, cnt: 8
Job#469227557 fetching data.
1956394918-- get val: 10, cnt: 9
Job#1680075645 fetching data.
469227557-- get val: 6, cnt: 5
498974849-- get val: 9, cnt: 8
1680075645-- get val: 5, cnt: 4
1470333138-- get val: 2, cnt: 1
186896924-- get val: 1, cnt: 0
760554585-- get val: 1, cnt: 0
Job#1336998605 finished.
Job#1956394918 finished.
Job#469227557 finished.
Job#498974849 finished.
Job#1680075645 finished.
Job#1470333138 finished.
Job#186896924 finished.
Job#760554585 finished.
Sum: 71
```
