program const_circle (input,output);
const
PI = 3.141592654;

var
r, d, c,c : real;   {variable declaration: radius, dia, circumference}

begin
   writeln('Enter the radius of the circle');
   readln(r);

   d := 2 * r;
   c :=  PI * d;
   writeln('The circumference of the circle is ',c);
end.
