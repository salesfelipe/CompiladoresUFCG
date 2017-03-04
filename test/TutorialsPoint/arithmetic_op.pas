program calculator;
var
a,b,c : integer;
d: real;

begin
   a:=21;
   b:=10;
   c := a + b;

   writeln(' Line 1 - Value of c is ', c );
   c := a - b;

   writeln('Line 2 - Value of c is ', c );
   c := a * b;

   writeln('Line 3 - Value of c is ', c );
   d := a / b;

   writeln('Line 4 - Value of d is ', d );
   c := a mod b;

   writeln('Line 5 - Value of c is ' , c );
   c := a div b;

   writeln('Line 6 - Value of c is ', c );
end.
