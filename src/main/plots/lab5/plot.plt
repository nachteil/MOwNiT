set title "Interpolation" font ",20"
set datafile separator ","
set term png enhanced size 1000,800

set grid
set xlabel "x"
set ylabel "y"

set output "test.png"
plot "spline.csv" u 1:2 with linespoints title "interpol",\
     "spline.csv" u 1:3 with linespoints title "orig"