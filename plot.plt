set title "Secant method in [0.1 ; 2.1]\n|x^{(i+1)} - x^{(i)}| < R\nStarting from variable point and 2.1" font ",20"
set datafile separator ","
set grid
set yrange [0.0:40.0]
set xrange [0.0:2.0]
set xlabel "Variable starting point"
set ylabel "Number of iterations"
set output "lab3_ex1_sec_iter_diff_v_b.png"


set term png enhanced size 1000,800


plot "datafile-3mhBv1dgkWEQ3R6.csv" u 1:($2+0.0) with linespoints title "R = 1,000e-05", \
"datafile-JUaTyhl0XXy5GYr.csv" u 1:($2+0.1) with linespoints title "R = 1,000e-07", \
"datafile-DktDhTeM9cONdak.csv" u 1:($2+0.2) with linespoints title "R = 1,000e-09", \
"datafile-P5RvydQq1UUwENQ.csv" u 1:($2+0.30000000000000004) with linespoints title "R = 1,000e-11", \
"datafile-de4P55LQ9LDD1nl.csv" u 1:($2+0.4) with linespoints title "R = 1,000e-13"

