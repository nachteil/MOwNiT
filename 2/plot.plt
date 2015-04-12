set term png
set output "jacobi_iteration_step.png"
set datafile separator ","
set logscale x
set grid
set dgrid3d
set title "Liczba iteracji (kryterium stopu - różnica \npomiędzy kolejnymi iteracjami) w zależności \nod precyzji i rozmiaru problemu n"
set xlabel "precyzja"
set ylabel "n"
set hidden3d
splot "jacobi_iteration_step.csv" u 2:1:3 with lines ti "Wynik"
