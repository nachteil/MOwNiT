set title "Interpolation error" font ",20"
set datafile separator ","
set grid
set xrange [0.0:50.0]
set xlabel "Number of interpolation points"
set ylabel "Error"
set output "interpolation-error.png"


set term png enhanced size 1000,800


plot "datafile-ZoytP1dlX8jmrLh.csv" u 1:2with linespoints title "Clamped spline - max. error", \
"datafile-rjHgPDG98ermZl0.csv" u 1:2with linespoints title "Clamped spline - integral error", \
"datafile-n6G2vxufdKiFYSs.csv" u 1:2with linespoints title "Natural spline - max. error", \
"datafile-9trjOXqBmK1vJDR.csv" u 1:2with linespoints title "Natural spline - max. error"

