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
   max := 'franklin best professor'; {max deveria retornar uma string}
end;

begin
   a := 1;
   b := 2;
end.
