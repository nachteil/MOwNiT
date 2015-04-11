set title "Tridiagonal problem solving time (float)" font ",20"
set datafile separator ","
set term png size 1000,800


set grid
set logscale y


set xlabel "Problem size N"
set ylabel "Time [ms]"

set output "ex3_float.png"
plot "lab1_ex3_float.csv" u 1:4 with points title "gaussian elimination", \
"lab1_ex3_float_diag.csv" u 1:4 with points title "tridiagonal method"

set title "Tridiagonal problem solving time (double)" font ",20"
set output "ex3_double.png"
plot "lab1_ex3_double.csv" u 1:4 with points title "gaussian elimination", \
"lab1_ex3_double_diag.csv" u 1:4 with points title "tridiagonal method"

set title "Tridiagonal problem solving time" font ",20"
set output "ex3_aggregate.png"
plot "lab1_ex3_float.csv" u 1:4 with points title "gaussian elimination (float)", \
"lab1_ex3_float_diag.csv" u 1:4 with points title "tridiagonal method (float)", \
"lab1_ex3_double.csv" u 1:4 with points title "gaussian elimination (double)", \
"lab1_ex3_double_diag.csv" u 1:4 with points title "tridiagonal method (double)"