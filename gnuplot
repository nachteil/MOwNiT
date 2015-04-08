set term png
set output "p.png"
plot "data.csv" u 1:2 w linespoints
