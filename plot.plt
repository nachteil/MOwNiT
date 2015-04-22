set title "Approximation" font ",20"
set datafile separator ","
set grid
set yrange [-2.0:4.0]
set xrange [0.0:11.0]
set xlabel "x"
set ylabel "y"
set output "out.png"


set term png enhanced size 800,600


plot "data-file-oqRbonaPQmqhnTo.csv" u 1:2 with linespoints title "Points"

