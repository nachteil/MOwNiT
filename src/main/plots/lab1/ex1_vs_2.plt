set title "Euclidian distance between original and calculated vectors" font ",20"
set datafile separator ","
set term png size 1000,800

set logscale y
set grid


set xlabel "Problem size N"
set ylabel "Euclidian distance"

set output "ex1_vs_2.png"
plot "lab1_ex_2_results_double.csv" u 1:3 with points title "ex-2-double", \
"lab1_ex_2_results_float.csv" u 1:3 with points title "ex-2-float", \
"lab1_results_double.csv" u 1:3 with points title "ex-1-double", \
"lab1_results_float.csv" u 1:3 with points title "ex-1-float"