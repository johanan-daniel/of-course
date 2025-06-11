import type { InputHTMLAttributes } from 'react'
import './field.css'

interface FieldProps extends InputHTMLAttributes<HTMLInputElement> {
    className?: string
}

// check type & props passed
export default function Field({ className = '', ...props }: FieldProps) {
    const combinedClassName = `field ${className}`

    return <input className={combinedClassName} {...props} />
}

export function BlockField({ className = '', ...props }: FieldProps) {
    return <Field className='block' {...props} />
}