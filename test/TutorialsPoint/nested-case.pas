program checknestedCase;
var
   a, b: integer;
begin
   a := 100;
   b := 200;

   case (a) of
      100: begin

         case (b) of
            200: ;
            end;
         end;
      end;
end.
