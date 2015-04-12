set title "Spectral radius of iteration matrices" font ",20"
set datafile separator ","
set term png size 1000,800

set grid

set xlabel "Matrix size N"
set ylabel "Spectral radius"

set yrange [0.8:0.9]

set output "ex2_radius.png"
plot "radius.csv" u 1:2 with linespoints title "Spectral radius"