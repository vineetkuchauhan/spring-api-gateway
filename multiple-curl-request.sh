for ((i=1;i<=1000;i++)); do  { echo $i; curl -LI "http://localhost:8080/ms1/get-data" -o /dev/null -w '%{http_code}\n' -s;} | sed ':a;N;s/\n/ /;ba'; done
