program exFunction;
var
   a, b, ret : integer;

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
	 max := 100;
   b := 200;
   ret := max(a, b); {a e b não foram instanciados}

end.
