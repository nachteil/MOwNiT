set title "Comparison based on different \polynomial degree\nNumber of approximation points: 25" font ",20"
set datafile separator ","
set grid
set yrange [-3.0:2.0]
set xrange [0.3333333333333333:3.0]
set xlabel "x"
set ylabel "y"
set output "out.png"


set term png enhanced size 800,600


plot "data-file-fWWSi78TWOm5Dv7.csv" u 1:2 with lines title "Original function", \
"data-file-eQPEEbO5qaUHifR.csv" u 1:2 with lines title "N = 1", \
"data-file-nTx5oWYfKZkvfWD.csv" u 1:2 with lines title "N = 9", \
"data-file-P2zojm1jeXGPwLZ.csv" u 1:2 with lines title "N = 17", \
"data-file-vhN0L5GM8NFxVIO.csv" u 1:2 with lines title "N = 25"

