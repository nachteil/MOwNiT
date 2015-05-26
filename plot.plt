set title "Comparison based on different \polynomial degree\nNumber of approximation points: 10" font ",20"
set datafile separator ","
set grid
set yrange [-3.0:2.0]
set xrange [0.3333333333333333:3.0]
set xlabel "x"
set ylabel "y"
set output "plot-CIYd11FGVz8ofuw.png"


set term png enhanced size 800,600


plot "datafile-6U3LhfyXzQ5vQGe.csv" u 1:2with lines title "original function", \
"datafile-znTDjcLCocyCO3e.csv" u 1:2with lines title "interpolation", \
"datafile-7UqU18NUaa3fDOB.csv" u 1:2with points title "interpolation points"

