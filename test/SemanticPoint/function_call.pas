program exFunction;
var
   a, b : string;
   c, d : integer;

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
   a := 'letra a';
   b := 'letra b';
   c := 100;
   d := 200;
   min(c, d); { a função min não existe }

end.
