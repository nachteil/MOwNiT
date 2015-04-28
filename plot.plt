set title "Interpolation for n = 50" font ",20"
set datafile separator ","
set grid
set xrange [0.0:3.5]
set xlabel "x"
set ylabel "y"
set output "lab4_ex1_n50_chebyschev.png"


set term png enhanced size 1000,800


plot "datafile-sWdIOrIv6W4gxTA.csv" u 1:2with lines title "Original function", \
"datafile-DA4pI9Bv2DQ2MCy.csv" u 1:2with lines title "Interpolation on Chebyschev nodes", \
"datafile-FO6J82LQlCG0C3o.csv" u 1:2with points title "Chebyschev interpolation nodes"

