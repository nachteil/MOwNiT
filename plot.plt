set title "Comparison based on different \polynomial degree\nNumber of approximation points: 25" font ",20"
set datafile separator ","
set grid
set yrange [-3.0:2.0]
set xrange [0.3333333333333333:3.0]
set xlabel "x"
set ylabel "y"
set output "out.png"


set term png enhanced size 800,600


plot "data-file-CXEwJbnzeszgnLC.csv" u 1:2 with lines title "Original function", \
"data-file-K11uzfyWXJyaNIH.csv" u 1:2 with lines title "N = 1", \
"data-file-oVFujSdmZiz6QSb.csv" u 1:2 with lines title "N = 9", \
"data-file-JTCqiYlac9H4Iu4.csv" u 1:2 with lines title "N = 17", \
"data-file-jWHo4c0nUb9mfzq.csv" u 1:2 with lines title "N = 25"

