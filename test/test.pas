program test;
const
   PI = 3.141592654;
var
   a, d : integer;
   c: real;
   s :string;
   b : boolean;
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
{inicio do programa}
begin
   a := 42;
   c := 3 + - 2 ;
   s := 'AnaFelipeRafael';
   b := true and not (5 > 6);
   c := max(-1, a);
end.
