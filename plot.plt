set title "Relative error of f(x) = x sin(3 PI / x) integral\nin [1/3;3] (Gauss-Legrande integration)" font ",20"
set datafile separator ","
set grid
set xrange [0.1:200000.0]
set xlabel "Number of intervals"
set ylabel "Relative error [%]"
set output "gauss-cotes.png"
set logscale x
set logscale y


set term png enhanced size 1000,800


plot "datafile-zuVDj7t6WV3V2DJ.csv" u 1:2with linespoints title "Quadrature degree: 0", \
"datafile-9zinF4q1JWTn0JZ.csv" u 1:2with linespoints title "Quadrature degree: 1", \
"datafile-Zv2GiA9JIcBQZO5.csv" u 1:2with linespoints title "Quadrature degree: 2", \
"datafile-6XpkYJ0AciJnfsT.csv" u 1:2with linespoints title "Quadrature degree: 3", \
"datafile-uzuQEHYvebrLwYH.csv" u 1:2with linespoints title "Quadrature degree: 4"

