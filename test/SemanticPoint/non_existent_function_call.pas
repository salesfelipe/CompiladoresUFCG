program exFunction;
var
   a, b, max : integer;

(*function definition *)
function ret(num1, num2: integer): integer;
var
   (* local variable declaration *)
   result: integer;
   c : integer;

begin
   if (num1 > num2) then
      result := num1

   else
      result := num2;
   ret := result;
end;

begin
   b := 10;
   a := ret(a, c);

end.
