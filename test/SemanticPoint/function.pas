program exFunction;
var
   a, b, ret : integer;
   c : string;

(*function definition *)
function max(num1, num2: integer; s1 : string): integer;
var
   (* local variable declaration *)
   result: integer;

begin
   if (num1 > num2) then
      result := num1

   else
      result := num2;
   max := num2
end;

begin
   a := 100;
   b := 200;
   c := 'bob marley';
   max(a,c,b);

   (* calling a function to get max value *)
  {funcao_nao_declarada(a, b);}

end.
