set title "Euclidian distance between original and calculated vectors" font ",20"
set datafile separator ","
set term png size 1000,800

set logscale y
set grid

set xlabel "Problem size N"
set ylabel "Euclidian distance"

set output "condition.png"
plot "lab1_condition_number_ex_2.csv" u 1:3 with points title "ex2 matrix", \
"lab1_condition_number_ex_1.csv" u 1:3 with points title "ex1 matrix"



set xrange [0:50]
set output "condition_small.png"
plot "lab1_condition_number_ex_2.csv" u 1:3 with points title "ex2 matrix", \
"lab1_condition_number_ex_1.csv" u 1:3 with points title "ex1 matrix"