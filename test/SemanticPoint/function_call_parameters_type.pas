program exFunction;
var
   i1, i2, max : integer;
   s1, s2, s3 : string;

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
   i1 := 100;
   i2 := 200;
   s1 := 'rafael';
   s2 := 'felipe';
   s3 := 'ana';

   max := ret(s1,s2,s3,i1,i2); { o correto é ret(int,int,string,string,string) }

end.
