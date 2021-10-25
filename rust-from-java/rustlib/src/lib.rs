mod rust_lib_test {

    #[no_mangle]
    pub extern "C" fn pow3(operand: i64) -> i64 {
        operand * operand * operand
    }
    
    #[cfg(test)]
    mod tests {
        use crate::rust_lib_test::pow3;

        #[test]
        fn it_works() {
            let result = pow3(2);
            assert_eq!(result, 8);
        }
    }
}
