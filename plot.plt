set title "Simple Plots" font ",20"
set datafile separator ","
set term png size 1000,800

set yrange [-5:5]
set xrange [0.5:3.5]

set output "regular.png"
set title "Interoplation on regular nodes"
plot "interpolation_reg.csv" u 1:2 with lines title "interpolation" lw 1, \
"function.csv" u 1:2 with lines title "original function" lw 1

set output "chebyschev.png"
set title "Interoplation on Chebyschev nodes"
plot "interpolation_cheb.csv" u 1:2 with lines title "intepolation",\
"function.csv" u 1:2 with lines title "original functino" lw 1
