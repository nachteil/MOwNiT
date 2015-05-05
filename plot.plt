set title "Relative error of f(x) = x sin(3 PI / x) integral\nin [1/3;3] (Gauss-Legrande integration)" font ",20"
set datafile separator ","
set grid
set xrange [0.1:1000.0]
set xlabel "Number of intervals"
set ylabel "Relative error [%]"
set output "gauss-cotes.png"
set logscale x
set logscale y


set term png enhanced size 1000,800


plot "datafile-eoDKrTjt0vn8wfX.csv" u 1:($2+0.0) with linespoints title "Quadrature degree: 0", \
"datafile-SRqKLA8z5VERITJ.csv" u 1:($2+0.1) with linespoints title "Quadrature degree: 1", \
"datafile-EIegeiwuzfsnDGP.csv" u 1:($2+0.2) with linespoints title "Quadrature degree: 2", \
"datafile-eLt4RL4W1buictP.csv" u 1:($2+0.30000000000000004) with linespoints title "Quadrature degree: 3", \
"datafile-7RlKhZN7WnhcbrN.csv" u 1:($2+0.4) with linespoints title "Quadrature degree: 4"

