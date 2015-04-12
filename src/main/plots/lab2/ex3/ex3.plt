set title "Number of iterations until stop (SOR, omega=1.2)" font ",20"
set datafile separator ","
set term png enhanced size 1000,800

set grid
set yrange [0:80]

set xlabel "Problem size N"
set ylabel "Number of iterations"

set output "ex3_iterations_vs_N.png"
plot "sor_iterations_prec3.csv" u 1:($3==1.2?$4:1/0) with points title "Subseq. iter. diff = 10^{-3}", \
"sor_iterations_prec5.csv" u 1:($3==1.2?$4:1/0) with points title "Subseq. iter. diff = 10^{-5}", \
"sor_iterations_prec9.csv" u 1:($3==1.2?$4:1/0) with points title "Subseq. iter. diff = 10^{-9}", \
"sor_result_diff_prec3.csv" u 1:($3==1.2?$4:1/0) with points title "Result deviation = 10^{-3}", \
"sor_result_diff_prec5.csv" u 1:($3==1.2?$4:1/0) with points title "Result deviation = 10^{-5}", \
"sor_result_diff_prec9.csv" u 1:($3==1.2?$4:1/0) with points title "Result deviation = 10^{-9}"

set logscale x
set xrange [1e-10:0.01]

set title "Number of iterations as function of precission (SOR, omega=1.2)"
set output "ex3_iterations_vs_precission_step.png"
set xlabel "Desired precission (stop criter parameter)"
set ylabel "Number of iterations"

plot "sor_iterations.csv" u 2:(($3==1.2 & $1==640)?$4:1/0) with points title "N = 640, stop based on iteration step" ps 3,\
"sor_result_diff.csv" u 2:(($3==1.2 & $1==640)?$4:1/0) with points title "N = 640, stop based on result deviation" ps 3

set title "Omega effect on number of iterations (N=640, precission = 10^{-9})

unset logscale x
unset logscale y
set xrange [0:2]
set yrange [0:100]

set xlabel "Omega"
set ylabel "Number of iterations"
set output "ex3_omega_vs_iterations.png"

set grid mxtics
set mxtics 5
set mytics 2

plot "sor_iterations_prec9.csv" u 3:(($1==640) ? $4 : 1/0) with points ps 3 title "iteration step based stop",\
"sor_result_diff_prec9.csv" u 3:(($1==640) ? $4 : 1/0) with points ps 3 title "result deviation based stop"