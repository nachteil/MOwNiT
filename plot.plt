set title "t=0.1000" font ",20"
set datafile separator ","
set grid
set yrange [0.0:10.0]
set xrange [0.0:1.0]
set xlabel "x"
set ylabel "u(x)"
set output "animation-1-frame999.png"


set term png enhanced size 1000,800


plot "datafile-k2Y40notBJu5UXX.csv" u 1:2with linespoints title "MES result"

