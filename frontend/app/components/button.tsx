import './button.css'

interface FieldProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
    className?: string
}

// check type & props passed
export default function Button({ className = '', children, ...props }: FieldProps) {
    const combinedClassName = `button ${className}`

    return <button className={combinedClassName} {...props}>{children}</button>
}

export function ButtonPrimary({ className = '', children, ...props }: FieldProps) {
    const combinedClassName = `primary ${className}`

    return <Button className={combinedClassName} {...props}>{children}</Button>
}