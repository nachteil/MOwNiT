set title "t=12.5664" font ",20"
set datafile separator ","
set grid
set yrange [-0.9999974750358044:50.26548245743668]
set xrange [0.0:10.0]
set xlabel "x"
set ylabel "u(x)"
set output "animation-1-frame699.png"


set term png enhanced size 800,600


plot "datafile-cE2SBcadfIU9Nhy.csv" u 1:2with linespoints title "MES result"

