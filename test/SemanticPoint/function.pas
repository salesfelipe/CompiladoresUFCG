program exFunction;
var
   a, b, ret : integer;

(*function definition *)
function max(num1, num2: integer; s1, s2, s3: string): string;
var
   (* local variable declaration *)
   result: integer;

begin
   if (num1 > num2) then
      result := num1

   else
      result := num2;
   max := s3
end;

begin
   a := 100;
   b := 200;
   (* calling a function to get max value *)
  {funcao_nao_declarada(a, b);}

end.
