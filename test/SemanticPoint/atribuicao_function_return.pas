program exFunction;
var
   a,b, ret : integer;
   c : string;

function max(num1, num2: integer): integer;
var
   (* local variable declaration *)
   result: integer;

begin
   if (num1 > num2) then
      result := num1

   else
      result := num2;
   max := result;
end;

begin
   c := max(a,b)
end.
