program exFunction;
var
   a, b : integer;
	 c, d : string;

(*function definition *)
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
   num1 := 10; { num1 não percence a esse escopo!!!}
   a := 200;
   b := 200;
   max(a, b);

end.
