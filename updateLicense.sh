#! /bin/bash

for file in `find -iname "*.scala"`;
do 
	diff <(head -n 17 LICENSE_ProgramFiles) <(head -n 17 $file) || `cat LICENSE_ProgramFiles | cat - $file > /tmp/out && mv /tmp/out $file`;
done