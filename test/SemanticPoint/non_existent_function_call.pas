program exFunction;
var
   a, b, max : integer;

(*function definition *)
function ret(num1, num2: integer; s1, s2, s3: string): integer;
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
   a := 100;
   b := 200;

   b := max(a,b);

end.