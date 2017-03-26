program exString;
uses sysutils;
var
   str1, str2, str3 : string;
   str4: string;
   len: integer;

begin
   str1 := 'Hello ';
   str2 := 'There!';

   (* copy str1 into str3 *)
   str3 := str1;

   str4 := str1 + str2;

end.
