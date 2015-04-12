#oba kryteria na jednym wykresie, iteracje vs N
#osobno kazde kryterium, iteracje vs precyzja
#oba kryteria na jednym wykresie, największe N, dokładność vs precyzja


set title "Number of iterations until stop" font ",20"
set datafile separator ","
set term png size 1000,800

set grid
set yrange [0:200]

set xlabel "Problem size N"
set ylabel "Number of iterations"

set output "ex1_iterations_vs_N.png"
plot "jacobi_iteration_prec3.csv" u 1:3 with linespoints title "Subseq. iter. diff = 10^{-3}", \
"jacobi_iteration_prec5.csv" u 1:3 with linespoints title "Subseq. iter. diff = 10^{-5}", \
"jacobi_iteration_prec9.csv" u 1:3 with linespoints title "Subseq. iter. diff = 10^{-9}", \
"jacobi_diff_prec3.csv" u 1:3 with linespoints title "Result deviation = 10^{-3}", \
"jacobi_diff_prec5.csv" u 1:3 with linespoints title "Result deviation = 10^{-5}", \
"jacobi_diff_prec9.csv" u 1:3 with linespoints title "Result deviation = 10^{-9}"

set logscale x
set xrange [1e-10:0.01]

set title "Number of iterations as function of precission"
set output "ex1_iterations_vs_precission_step.png"
set xlabel "Desired precission (stop criter parameter)"
set ylabel "Number of iterations"

plot "jacobi_step_big_N.csv" u 2:3 with points title "N > 900, stop based on iteration step" ps 3,\
"jacobi_diff_big_N.csv" u 2:3 with points title "N > 900, stop based on result deviation" ps 3

set title "Result accuracy vs. precision used"
set output "ex1_accuracy_vs_precision.png"

set yrange [1e-12:0.1]
set logscale y
set xlabel "Desired precission (stop criter parameter)"
set ylabel "Difference between original and calculated value"

plot "jacobi_step_big_N.csv" u 2:4 with points title "Iteration step based stop" ps 3,\
"jacobi_diff_big_N.csv" u 2:4 with points title "Result deviation based stop" ps 3