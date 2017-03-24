program exFunction;
var
   a, b : integer;

function max(num1, num2: integer): integer;
var
   result: integer;

begin
   if (num1 > num2) then
      result := num1
   else
      result := num2;

   max := 2;
end;

begin
   a := 1;
   b := 2;
   max(a, b);
end.
