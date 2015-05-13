set title "MES Solution" font ",20"
set datafile separator ","
set grid
set xrange [-0.6000000000000001:3.6]
set xlabel "x"
set ylabel "y"
set output "mes.png"


set term png enhanced size 1000,800


plot "datafile-vghC88r44k9CCVD.csv" u 1:2with linespoints title "MES Solution points", \
"datafile-aNAr7DHIbxi382u.csv" u 1:2with lines title "Exact solution"

