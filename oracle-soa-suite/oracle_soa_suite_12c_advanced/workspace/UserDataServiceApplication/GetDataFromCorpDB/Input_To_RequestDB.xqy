xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns1="http://xmlns.oracle.com/pcbpel/adapter/db/CustomSQL";
(:: import schema at "Resources/CustomSQL.xsd" ::)

declare variable $input_arg_age as xs:int external;

declare function local:func($input_arg_age as xs:int) as element() (:: schema-element(ns1:CustomSQLInput) ::) {
    <ns1:CustomSQLInput>
        <ns1:age_parameter>{fn:data($input_arg_age)}</ns1:age_parameter>
    </ns1:CustomSQLInput>
};

local:func($input_arg_age)
